import mongoose, { Schema, type HydratedDocument, type Model } from 'mongoose';

const CUSTOMER_STATUSES = ['active', 'inactive'] as const;
export type CustomerStatus = (typeof CUSTOMER_STATUSES)[number];

export interface Customer {
  shopId: mongoose.Types.ObjectId;
  name: string;
  phone: string;
  creditLimitMinor: number;
  openingBalanceMinor: number;
  status: CustomerStatus;
}

export type CustomerDocument = HydratedDocument<Customer>;

const customerSchema = new Schema<Customer>(
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
      minlength: 2,
      maxlength: 120,
    },
    phone: {
      type: String,
      required: true,
      trim: true,
      maxlength: 20,
    },
    creditLimitMinor: {
      type: Number,
      required: true,
      default: 0,
      min: 0,
    },
    openingBalanceMinor: {
      type: Number,
      required: true,
      default: 0,
    },
    status: {
      type: String,
      enum: CUSTOMER_STATUSES,
      default: 'active',
      required: true,
    },
  },
  {
    collection: 'customers',
    timestamps: true,
    strict: true,
    versionKey: false,
  },
);

customerSchema.index({ shopId: 1, phone: 1 });

export const CustomerModel: Model<Customer> =
  mongoose.models.Customer ?? mongoose.model<Customer>('Customer', customerSchema);
