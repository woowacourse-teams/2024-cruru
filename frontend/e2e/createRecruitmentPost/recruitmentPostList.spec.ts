import { test, expect } from '@playwright/test';
import { routes } from '@router/path';

test.describe('공고 목록 페이지', () => {
  test.beforeEach(async ({ page }) => {
    // 로그인
    await page.goto(routes.signIn());
    await page.fill('input[name="email"]', 'member@mail.com');
    await page.fill('input[name="password"]', 'qwer1234');
    await page.click('button[type="submit"]');

    await page.waitForURL(routes.dashboard.list());
  });

  test.describe('[Render] 공고 목록 페이지의 UI 요소가 올바르게 렌더링되는지 테스트한다.', () => {
    test.skip('공고 목록 페이지의 타이틀이 올바르게 렌더링된다.', async ({ page }) => {
      const title = await page.locator('h1');
      expect(await title.isVisible()).toBeTruthy();
    });

    test('카드가 존재하는 경우 공고 제목, 모집 상태, 마감일, 지원자 현황(전체, 평가대상, 불합격, 합격)이 렌더링된다.', async ({
      page,
    }) => {
      await page.waitForSelector('article');

      // 카드의 존재 여부 확인
      const card = await page.locator('article').nth(0);

      // 공고 제목 확인
      const recruitInfo = await card.locator('div').first();
      const title = await recruitInfo.locator('div').nth(0);
      expect(await title.isVisible()).toBeTruthy();

      // 모집 상태 확인
      const status = await recruitInfo.locator('div').nth(1);
      expect(await status.isVisible()).toBeTruthy();

      // 마감일 확인
      const deadline = await recruitInfo.locator('div').nth(2);
      expect(await deadline.isVisible()).toBeTruthy();

      // 지원자 현황 확인
      const total = await card.locator('div:has-text("전체")').locator('span').first();
      const evaluated = await card.locator('div:has-text("평가 대상")').locator('span').first();
      const failed = await card.locator('div:has-text("불합격")').locator('span').first();
      const passed = await card.locator('div:has-text("합격")').locator('span').first();

      // 각각의 요소가 보이는지 확인
      expect(await total.isVisible()).toBeTruthy();
      expect(await evaluated.isVisible()).toBeTruthy();
      expect(await failed.isVisible()).toBeTruthy();
      expect(await passed.isVisible()).toBeTruthy();
    });
  });

  test.describe('[Action] 공고 목록 페이지의 기능이 올바르게 동작하는지 테스트한다.', () => {
    test('공고 카드를 클릭하면 공고 대시보드 페이지로 이동한다.', async ({ page }) => {
      await page.waitForSelector('article');

      const card = await page.locator('article').nth(0);
      await card.click();

      const currentPath = new URL(page.url()).pathname;
      const dashboardPathPattern = /^\/dashboard\/\d+\/\d+$/;
      expect(currentPath).toMatch(dashboardPathPattern);
    });

    test('새 공고 추가 버튼을 클릭하면 공고 생성 페이지로 이동한다.', async ({ page }) => {
      const createButton = await page.getByText('새 공고 추가');
      await createButton.click();

      const currentPath = new URL(page.url()).pathname;
      const createPathPattern = /^\/dashboard\/create$/;
      expect(currentPath).toMatch(createPathPattern);
    });
  });
});
