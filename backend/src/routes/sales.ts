import { Router, type Request, type Response } from 'express';
import { SaleModel } from '../models/Sale.js';
import { TransactionModel } from '../models/Transaction.js';
import { ProductModel } from '../models/Product.js';
import { requireAuth } from '../middleware/auth.js';

const router = Router();

router.get('/', requireAuth, async (req: Request, res: Response): Promise<void> => {
  try {
    const sales = await SaleModel.find({ shopId: req.user?.shopId })
      .populate('customerId', 'name phone')
      .sort({ saleDate: -1 });
    res.json(sales);
  } catch (error) {
    res.status(500).json({ error: (error as Error).message });
  }
});

router.post('/', requireAuth, async (req: Request, res: Response): Promise<void> => {
  try {
    const { customerId, invoiceNumber, items, subtotalMinor, totalMinor, paidMinor, creditMinor, paymentMethod } = req.body;

    const sale = await SaleModel.create({
      shopId: req.user?.shopId,
      customerId: customerId || undefined,
      invoiceNumber: invoiceNumber || `INV-${Date.now().toString().slice(-6)}`,
      items,
      subtotalMinor: subtotalMinor || totalMinor,
      totalMinor,
      paidMinor: paidMinor ?? totalMinor,
      creditMinor: creditMinor ?? 0,
      paymentMethod: paymentMethod || 'cash',
      status: 'completed',
      saleDate: new Date(),
    });

    if (items && Array.isArray(items)) {
      for (const item of items) {
        await ProductModel.updateOne(
          { _id: item.productId, shopId: req.user?.shopId },
          { $inc: { stockQuantity: -item.quantity } }
        );
      }
    }

    await TransactionModel.create({
      shopId: req.user?.shopId,
      type: 'sale',
      saleId: sale._id,
      customerId: customerId || undefined,
      amountMinor: totalMinor,
      direction: 'credit',
      paymentMethod: paymentMethod || 'cash',
      occurredAt: new Date(),
    });

    res.status(201).json(sale);
  } catch (error) {
    res.status(500).json({ error: (error as Error).message });
  }
});

export const saleRouter = router;
