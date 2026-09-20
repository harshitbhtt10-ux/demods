import { Router, type Request, type Response } from 'express';
import { GoogleGenerativeAI } from '@google/generative-ai';
import { env } from '../config/env.js';
import { ProductModel } from '../models/Product.js';
import { CustomerModel } from '../models/Customer.js';
import { SaleModel } from '../models/Sale.js';
import { requireAuth } from '../middleware/auth.js';

const router = Router();
const genAI = new GoogleGenerativeAI(env.GEMINI_API_KEY);

router.post('/query', requireAuth, async (req: Request, res: Response): Promise<void> => {
  try {
    const { query } = req.body;
    if (!query) {
      res.status(400).json({ error: 'Query is required' });
      return;
    }

    const shopId = req.user?.shopId;
    const products = await ProductModel.find({ shopId });
    const customers = await CustomerModel.find({ shopId });
    const sales = await SaleModel.find({ shopId }).sort({ saleDate: -1 }).limit(10);

    const context = `
Store Data Summary:
- Total Products: ${products.length}
- Low Stock Items: ${products.filter(p => p.stockQuantity <= p.reorderLevel).map(p => p.name).join(', ')}
- Total Customers: ${customers.length}
- Recent Sales Count: ${sales.length}
    `;

    const model = genAI.getGenerativeModel({ model: 'gemini-1.5-flash' });
    const prompt = `You are DukaanSetu AI, an expert shop assistant for Indian kirana/retail store owners. Answer queries accurately based on the store context in Hindi-English (Hinglish) or English as appropriate, keeping it helpful and concise.\n\nContext:\n${context}\n\nQuestion: ${query}`;

    const result = await model.generateContent(prompt);
    const reply = result.response.text() || 'Main samajh nahi paya, kripya dobara puchein.';

    res.json({ reply });
  } catch (error) {
    res.status(500).json({ error: (error as Error).message });
  }
});

export const aiRouter = router;
