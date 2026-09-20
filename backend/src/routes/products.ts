import { Router, type Request, type Response } from 'express';
import { ProductModel } from '../models/Product.js';
import { requireAuth } from '../middleware/auth.js';

const router = Router();

router.get('/', requireAuth, async (req: Request, res: Response): Promise<void> => {
  try {
    const products = await ProductModel.find({ shopId: req.user?.shopId }).sort({ createdAt: -1 });
    res.json(products);
  } catch (error) {
    res.status(500).json({ error: (error as Error).message });
  }
});

router.post('/', requireAuth, async (req: Request, res: Response): Promise<void> => {
  try {
    const { name, sku, category, unit, costPriceMinor, sellingPriceMinor, stockQuantity, reorderLevel } = req.body;
    const product = await ProductModel.create({
      shopId: req.user?.shopId,
      name,
      sku: sku || `SKU-${Date.now().toString().slice(-6)}`,
      category: category || 'General',
      unit: unit || 'piece',
      costPriceMinor: costPriceMinor || 0,
      sellingPriceMinor: sellingPriceMinor || 0,
      stockQuantity: stockQuantity || 0,
      reorderLevel: reorderLevel || 10,
    });
    res.status(201).json(product);
  } catch (error) {
    res.status(500).json({ error: (error as Error).message });
  }
});

router.put('/:id', requireAuth, async (req: Request, res: Response): Promise<void> => {
  try {
    const product = await ProductModel.findOneAndUpdate(
      { _id: req.params.id, shopId: req.user?.shopId },
      req.body,
      { new: true, runValidators: true }
    );
    if (!product) {
      res.status(404).json({ error: 'Product not found' });
      return;
    }
    res.json(product);
  } catch (error) {
    res.status(500).json({ error: (error as Error).message });
  }
});

router.delete('/:id', requireAuth, async (req: Request, res: Response): Promise<void> => {
  try {
    const product = await ProductModel.findOneAndDelete({ _id: req.params.id, shopId: req.user?.shopId });
    if (!product) {
      res.status(404).json({ error: 'Product not found' });
      return;
    }
    res.json({ message: 'Product deleted successfully' });
  } catch (error) {
    res.status(500).json({ error: (error as Error).message });
  }
});

export const productRouter = router;
