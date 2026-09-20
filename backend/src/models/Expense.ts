import mongoose, { Schema, type HydratedDocument, type Model } from 'mongoose';

export interface Expense {
  shopId: mongoose.Types.ObjectId;
  category: string;
  description: string;
  amountMinor: number;
  paymentMethod: string;
  occurredAt: Date;
}

export type ExpenseDocument = HydratedDocument<Expense>;

const expenseSchema = new Schema<Expense>(
  {
    shopId: { type: Schema.Types.ObjectId, ref: 'Shop', required: true, index: true },
    category: { type: String, required: true, trim: true },
    description: { type: String, required: true, trim: true },
    amountMinor: { type: Number, required: true, min: 0 },
    paymentMethod: { type: String, default: 'cash', required: true },
    occurredAt: { type: Date, default: Date.now, required: true },
  },
  {
    collection: 'expenses',
    timestamps: true,
    strict: true,
    versionKey: false,
  }
);

expenseSchema.index({ shopId: 1, occurredAt: -1 });

export const ExpenseModel: Model<Expense> =
  mongoose.models.Expense ?? mongoose.model<Expense>('Expense', expenseSchema);
