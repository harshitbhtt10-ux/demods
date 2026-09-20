import mongoose, { Schema, type HydratedDocument, type Model } from 'mongoose';

const TRANSACTION_TYPES = ['sale', 'payment', 'expense'] as const;
const DIRECTIONS = ['credit', 'debit'] as const;

export interface Transaction {
  shopId: mongoose.Types.ObjectId;
  type: (typeof TRANSACTION_TYPES)[number];
  saleId?: mongoose.Types.ObjectId;
  expenseId?: mongoose.Types.ObjectId;
  customerId?: mongoose.Types.ObjectId;
  amountMinor: number;
  direction: (typeof DIRECTIONS)[number];
  paymentMethod: string;
  occurredAt: Date;
}

export type TransactionDocument = HydratedDocument<Transaction>;

const transactionSchema = new Schema<Transaction>(
  {
    shopId: { type: Schema.Types.ObjectId, ref: 'Shop', required: true, index: true },
    type: { type: String, enum: TRANSACTION_TYPES, required: true },
    saleId: { type: Schema.Types.ObjectId, ref: 'Sale' },
    expenseId: { type: Schema.Types.ObjectId, ref: 'Expense' },
    customerId: { type: Schema.Types.ObjectId, ref: 'Customer', index: true },
    amountMinor: { type: Number, required: true, min: 0 },
    direction: { type: String, enum: DIRECTIONS, required: true },
    paymentMethod: { type: String, required: true },
    occurredAt: { type: Date, default: Date.now, required: true },
  },
  {
    collection: 'transactions',
    timestamps: true,
    strict: true,
    versionKey: false,
  }
);

transactionSchema.index({ shopId: 1, occurredAt: -1 });

export const TransactionModel: Model<Transaction> =
  mongoose.models.Transaction ?? mongoose.model<Transaction>('Transaction', transactionSchema);
