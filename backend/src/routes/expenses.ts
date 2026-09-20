import { Router, type Request, type Response } from 'express';
import { ExpenseModel } from '../models/Expense.js';
import { TransactionModel } from '../models/Transaction.js';
import { requireAuth } from '../middleware/auth.js';

const router = Router();

router.get('/', requireAuth, async (req: Request, res: Response): Promise<void> => {
  try {
    const expenses = await ExpenseModel.find({ shopId: req.user?.shopId }).sort({ occurredAt: -1 });
    res.json(expenses);
  } catch (error) {
    res.status(500).json({ error: (error as Error).message });
  }
});

router.post('/', requireAuth, async (req: Request, res: Response): Promise<void> => {
  try {
    const { category, description, amountMinor, paymentMethod } = req.body;
    const expense = await ExpenseModel.create({
      shopId: req.user?.shopId,
      category,
      description,
      amountMinor,
      paymentMethod: paymentMethod || 'cash',
      occurredAt: new Date(),
    });

    await TransactionModel.create({
      shopId: req.user?.shopId,
      type: 'expense',
      expenseId: expense._id,
      amountMinor,
      direction: 'debit',
      paymentMethod: paymentMethod || 'cash',
      occurredAt: new Date(),
    });

    res.status(201).json(expense);
  } catch (error) {
    res.status(500).json({ error: (error as Error).message });
  }
});

export const expenseRouter = router;
