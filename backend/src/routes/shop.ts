import { Router, type Request, type Response } from 'express';
import { ShopModel } from '../models/Shop.js';
import { requireAuth } from '../middleware/auth.js';

const router = Router();

router.get('/', requireAuth, async (req: Request, res: Response): Promise<void> => {
  try {
    const shop = await ShopModel.findById(req.user?.shopId);
    if (!shop) {
      res.status(404).json({ error: 'Shop not found' });
      return;
    }
    res.json(shop);
  } catch (error) {
    res.status(500).json({ error: (error as Error).message });
  }
});

router.put('/', requireAuth, async (req: Request, res: Response): Promise<void> => {
  try {
    const shop = await ShopModel.findByIdAndUpdate(req.user?.shopId, req.body, { new: true, runValidators: true });
    if (!shop) {
      res.status(404).json({ error: 'Shop not found' });
      return;
    }
    res.json(shop);
  } catch (error) {
    res.status(500).json({ error: (error as Error).message });
  }
});

export const shopRouter = router;
