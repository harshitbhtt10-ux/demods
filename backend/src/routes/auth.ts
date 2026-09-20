import { Router, type Request, type Response } from 'express';
import bcrypt from 'bcryptjs';
import jwt from 'jsonwebtoken';
import { UserModel } from '../models/User.js';
import { ShopModel } from '../models/Shop.js';
import { env } from '../config/env.js';
import { requireAuth } from '../middleware/auth.js';

const router = Router();

router.post('/register', async (req: Request, res: Response): Promise<void> => {
  try {
    const { shopName, name, email, password } = req.body;
    if (!shopName || !name || !email || !password) {
      res.status(400).json({ error: 'All fields are required' });
      return;
    }

    const existingUser = await UserModel.findOne({ email });
    if (existingUser) {
      res.status(400).json({ error: 'Email already registered' });
      return;
    }

    const slug = shopName
      .toLowerCase()
      .replace(/[^a-z0-9]+/g, '-')
      .replace(/(^-|-$)/g, '');

    let shop = await ShopModel.findOne({ slug });
    if (!shop) {
      shop = await ShopModel.create({
        name: shopName,
        slug,
        currency: 'INR',
        timezone: 'Asia/Kolkata',
        settings: { allowNegativeInventory: false, lowStockNotifications: true, khataReminders: true },
        status: 'active',
      });
    }

    const passwordHash = await bcrypt.hash(password, 10);
    const user = await UserModel.create({
      shopId: shop._id,
      name,
      email,
      passwordHash,
      role: 'owner',
    });

    const token = jwt.sign(
      { userId: user._id.toString(), shopId: shop._id.toString(), email: user.email, role: user.role },
      env.JWT_SECRET,
      { expiresIn: '7d' }
    );

    res.status(201).json({
      token,
      user: { id: user._id, name: user.name, email: user.email, role: user.role },
      shop: { id: shop._id, name: shop.name, slug: shop.slug },
    });
  } catch (error) {
    res.status(500).json({ error: (error as Error).message });
  }
});

router.post('/login', async (req: Request, res: Response): Promise<void> => {
  try {
    const { email, password } = req.body;
    if (!email || !password) {
      res.status(400).json({ error: 'Email and password are required' });
      return;
    }

    const user = await UserModel.findOne({ email });
    if (!user) {
      res.status(401).json({ error: 'Invalid email or password' });
      return;
    }

    const isMatch = await bcrypt.compare(password, user.passwordHash);
    if (!isMatch) {
      res.status(401).json({ error: 'Invalid email or password' });
      return;
    }

    const shop = await ShopModel.findById(user.shopId);

    const token = jwt.sign(
      { userId: user._id.toString(), shopId: user.shopId.toString(), email: user.email, role: user.role },
      env.JWT_SECRET,
      { expiresIn: '7d' }
    );

    res.json({
      token,
      user: { id: user._id, name: user.name, email: user.email, role: user.role },
      shop: shop ? { id: shop._id, name: shop.name, slug: shop.slug } : null,
    });
  } catch (error) {
    res.status(500).json({ error: (error as Error).message });
  }
});

router.get('/me', requireAuth, async (req: Request, res: Response): Promise<void> => {
  try {
    const user = await UserModel.findById(req.user?.userId).select('-passwordHash');
    const shop = await ShopModel.findById(req.user?.shopId);
    res.json({ user, shop });
  } catch (error) {
    res.status(500).json({ error: (error as Error).message });
  }
});

export const authRouter = router;
