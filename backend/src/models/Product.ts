import mongoose, { Schema, type HydratedDocument, type Model } from 'mongoose';

export interface Product {
  shopId: mongoose.Types.ObjectId;
  name: string;
  sku: string;
  category: string;
  unit: string;
  costPriceMinor: number;
  sellingPriceMinor: number;
  stockQuantity: number;
  reorderLevel: number;
}

export type ProductDocument = HydratedDocument<Product>;

const productSchema = new Schema<Product>(
  {
    shopId: {
      type: Schema.Types.ObjectId,
      ref: 'Shop',
      required: true,
      index: true,
    },
    name: {
      type: String,
      required: true,
      trim: true,
      minlength: 1,
      maxlength: 150,
    },
    sku: {
      type: String,
      required: true,
      trim: true,
      uppercase: true,
    },
    category: {
      type: String,
      required: true,
      trim: true,
    },
    unit: {
      type: String,
      required: true,
      default: 'piece',
    },
    costPriceMinor: {
      type: Number,
      required: true,
      min: 0,
    },
    sellingPriceMinor: {
      type: Number,
      required: true,
      min: 0,
    },
    stockQuantity: {
      type: Number,
      required: true,
      default: 0,
      min: 0,
    },
    reorderLevel: {
      type: Number,
      required: true,
      default: 10,
      min: 0,
    },
  },
  {
    collection: 'products',
    timestamps: true,
    strict: true,
    versionKey: false,
  },
);

productSchema.index({ shopId: 1, sku: 1 }, { unique: true });

export const ProductModel: Model<Product> =
  mongoose.models.Product ?? mongoose.model<Product>('Product', productSchema);
