import mongoose, { Schema, type HydratedDocument, type Model } from 'mongoose';

const USER_ROLES = ['owner', 'staff'] as const;
export type UserRole = (typeof USER_ROLES)[number];

export interface User {
  organizationId?: mongoose.Types.ObjectId;
  shopId: mongoose.Types.ObjectId;
  name: string;
  email: string;
  passwordHash: string;
  role: UserRole;
}

export type UserDocument = HydratedDocument<User>;

const userSchema = new Schema<User>(
  {
    organizationId: {
      type: Schema.Types.ObjectId,
      ref: 'Organization',
      index: true,
    },
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
      maxlength: 100,
    },
    email: {
      type: String,
      required: true,
      unique: true,
      trim: true,
      lowercase: true,
    },
    passwordHash: {
      type: String,
      required: true,
    },
    role: {
      type: String,
      enum: USER_ROLES,
      default: 'owner',
      required: true,
    },
  },
  {
    collection: 'users',
    timestamps: true,
    strict: true,
    versionKey: false,
  },
);

export const UserModel: Model<User> =
  mongoose.models.User ?? mongoose.model<User>('User', userSchema);
