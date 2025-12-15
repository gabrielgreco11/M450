import { test, expect } from '@playwright/test';


test.describe('Angular App E2E Test', () => {


test('startseite laden und Titel prüfen', async ({ page }) => {
await page.goto('/students');


await expect(page.locator('h1')).toHaveText('Meine Angular App');
});

 test('add a new student', async ({ page }) => {
    await page.goto('/addstudents');

    await page.fill('input[name="email"]', 'gabriel@mail.com');
    await page.fill('input[name="name"]', 'gabriel');

    await page.click('button[type="submit"]');

    const studentRow = page.locator('text=name');
    await expect(studentRow).toBeVisible();
  });




});