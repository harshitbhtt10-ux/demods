import { Router, type Request, type Response } from 'express';
import { CustomerModel } from '../models/Customer.js';
import { requireAuth } from '../middleware/auth.js';

const router = Router();

router.get('/', requireAuth, async (req: Request, res: Response): Promise<void> => {
  try {
    const customers = await CustomerModel.find({ shopId: req.user?.shopId }).sort({ createdAt: -1 });
    res.json(customers);
  } catch (error) {
    res.status(500).json({ error: (error as Error).message });
  }
});

router.post('/', requireAuth, async (req: Request, res: Response): Promise<void> => {
  try {
    const { name, phone, creditLimitMinor, openingBalanceMinor, status } = req.body;
    const customer = await CustomerModel.create({
      shopId: req.user?.shopId,
      name,
      phone,
      creditLimitMinor: creditLimitMinor || 500000,
      openingBalanceMinor: openingBalanceMinor || 0,
      status: status || 'active',
    });
    res.status(201).json(customer);
  } catch (error) {
    res.status(500).json({ error: (error as Error).message });
  }
});

router.put('/:id', requireAuth, async (req: Request, res: Response): Promise<void> => {
  try {
    const customer = await CustomerModel.findOneAndUpdate(
      { _id: req.params.id, shopId: req.user?.shopId },
      req.body,
      { new: true, runValidators: true }
    );
    if (!customer) {
      res.status(404).json({ error: 'Customer not found' });
      return;
    }
    res.json(customer);
  } catch (error) {
    res.status(500).json({ error: (error as Error).message });
  }
});

export const customerRouter = router;
