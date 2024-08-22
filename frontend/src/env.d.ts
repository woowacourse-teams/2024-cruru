declare namespace NodeJS {
  interface ProcessEnv {
    API_URL: string;
    API_VERSION: string;
    SENTRY_DSN: string;
    SENTRY_AUTH_TOKEN: string;
    GA_MEASUREMENT_ID: string;
    PROD_URL: string;
    DEV_URL: string;
  }
}
