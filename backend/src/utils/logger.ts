export const logger = {
  success: (message: string, meta?: unknown) => {
    console.log(`[SUCCESS] ${message}`, meta ? meta : '');
  },
  info: (message: string, meta?: unknown) => {
    console.log(`[INFO] ${message}`, meta ? meta : '');
  },
  warn: (message: string, meta?: unknown) => {
    console.warn(`[WARN] ${message}`, meta ? meta : '');
  },
  error: (message: string, meta?: unknown) => {
    console.error(`[ERROR] ${message}`, meta ? meta : '');
  },
};
