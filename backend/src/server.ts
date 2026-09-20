import express from 'express';
import cors from 'cors';
import rateLimit from 'express-rate-limit';
import { env } from './config/env.js';
import { connectToDatabase } from './config/database.js';
import { logger } from './utils/logger.js';
import { authRouter } from './routes/auth.js';
import { shopRouter } from './routes/shop.js';
import { productRouter } from './routes/products.js';
import { customerRouter } from './routes/customers.js';
import { saleRouter } from './routes/sales.js';
import { expenseRouter } from './routes/expenses.js';
import { aiRouter } from './routes/ai.js';

const app = express();

app.use(cors({ origin: env.FRONTEND_URL, credentials: true }));
app.use(express.json());

const limiter = rateLimit({
  windowMs: 15 * 60 * 1000,
  max: 100,
  standardHeaders: true,
  legacyHeaders: false,
});
app.use(limiter);

app.use('/api/auth', authRouter);
app.use('/api/shop', shopRouter);
app.use('/api/products', productRouter);
app.use('/api/customers', customerRouter);
app.use('/api/sales', saleRouter);
app.use('/api/expenses', expenseRouter);
app.use('/api/ai', aiRouter);

app.get('/health', (_req, res) => {
  res.json({ status: 'ok', timestamp: new Date().toISOString() });
});

async function startServer() {
  try {
    await connectToDatabase();
    app.listen(Number(env.PORT), '0.0.0.0', () => {
      logger.success(`SERVER_STARTED_ON_PORT_${env.PORT}`);
    });
  } catch (error) {
    logger.error('SERVER_START_FAILED', { errorType: error instanceof Error ? error.name : 'UnknownError' });
    process.exit(1);
  }
}

startServer();
