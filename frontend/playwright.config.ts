import { defineConfig, devices } from '@playwright/test';

import dotenv from 'dotenv';
import path from 'path';

dotenv.config({ path: path.resolve(__dirname, '.env.local') });

export default defineConfig({
  testDir: './e2e',
  fullyParallel: true,
  // reporter: [['./e2e/slack-reporter.ts'], ['html'], ['list']],
  reporter: [['html'], ['list']],

  use: {
    /* `await page.goto('/')`와 같은 액션에서 사용할 기본 URL 설정 */
    baseURL: process.env.DOMAIN_URL,
    trace: 'on-first-retry',
  },

  projects: [
    {
      name: 'chromium',
      use: { ...devices['Desktop Chrome'] },
    },

    {
      name: 'firefox',
      use: { ...devices['Desktop Firefox'] },
    },

    {
      name: 'webkit',
      use: { ...devices['Desktop Safari'] },
    },

    /* 소스 코드에 test.only를 남겨둔 경우, CI에서 빌드를 실패 처리합니다. */
    // forbidOnly: !!process.env.CI,
    /* CI 환경에서만 재시도 설정 */
    // retries: process.env.CI ? 2 : 0,
    /* CI 환경에서 병렬 테스트 실행 비활성화 */
    // workers: process.env.CI ? 1 : undefined,

    /* 모바일 뷰포트에 대한 테스트 */
    // {
    //   name: 'Mobile Chrome',
    //   use: { ...devices['Pixel 5'] },
    // },
    // {
    //   name: 'Mobile Safari',
    //   use: { ...devices['iPhone 12'] },
    // },

    /* 브랜디드 브라우저에 대한 테스트 */
    // {
    //   name: 'Microsoft Edge',
    //   use: { ...devices['Desktop Edge'], channel: 'msedge' },
    // },
    // {
    //   name: 'Google Chrome',
    //   use: { ...devices['Desktop Chrome'], channel: 'chrome' },
    // },
  ],

  /* 테스트를 시작하기 전에 로컬 개발 서버 실행 */
  // webServer: {
  //   command: 'npm run start',
  //   url: 'http://127.0.0.1:3000',
  //   reuseExistingServer: !process.env.CI,
  // },
});
