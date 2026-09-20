import mongoose, { Schema, type HydratedDocument, type Model } from 'mongoose';

export interface SaleItem {
  productId: mongoose.Types.ObjectId;
  quantity: number;
  unitPriceMinor: number;
  lineTotalMinor: number;
}

const PAYMENT_METHODS = ['cash', 'upi', 'credit', 'card'] as const;
const SALE_STATUSES = ['completed', 'cancelled', 'pending'] as const;

export interface Sale {
  shopId: mongoose.Types.ObjectId;
  customerId?: mongoose.Types.ObjectId;
  invoiceNumber: string;
  items: SaleItem[];
  subtotalMinor: number;
  totalMinor: number;
  paidMinor: number;
  creditMinor: number;
  paymentMethod: (typeof PAYMENT_METHODS)[number];
  status: (typeof SALE_STATUSES)[number];
  saleDate: Date;
}

export type SaleDocument = HydratedDocument<Sale>;

const saleItemSchema = new Schema<SaleItem>(
  {
    productId: { type: Schema.Types.ObjectId, ref: 'Product', required: true },
    quantity: { type: Number, required: true, min: 1 },
    unitPriceMinor: { type: Number, required: true, min: 0 },
    lineTotalMinor: { type: Number, required: true, min: 0 },
  },
  { _id: false }
);

const saleSchema = new Schema<Sale>(
  {
    shopId: { type: Schema.Types.ObjectId, ref: 'Shop', required: true, index: true },
    customerId: { type: Schema.Types.ObjectId, ref: 'Customer', index: true },
    invoiceNumber: { type: String, required: true, trim: true },
    items: { type: [saleItemSchema], required: true },
    subtotalMinor: { type: Number, required: true, min: 0 },
    totalMinor: { type: Number, required: true, min: 0 },
    paidMinor: { type: Number, required: true, min: 0 },
    creditMinor: { type: Number, required: true, min: 0 },
    paymentMethod: { type: String, enum: PAYMENT_METHODS, default: 'cash', required: true },
    status: { type: String, enum: SALE_STATUSES, default: 'completed', required: true },
    saleDate: { type: Date, default: Date.now, required: true },
  },
  {
    collection: 'sales',
    timestamps: true,
    strict: true,
    versionKey: false,
  }
);

saleSchema.index({ shopId: 1, invoiceNumber: 1 }, { unique: true });
saleSchema.index({ shopId: 1, saleDate: -1 });

export const SaleModel: Model<Sale> =
  mongoose.models.Sale ?? mongoose.model<Sale>('Sale', saleSchema);
