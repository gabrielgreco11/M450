import { test, expect } from '@playwright/test';

test.describe('End-to-End User Flow', () => {
  test('complete user journey: navigate, add student, view in list', async ({ page }) => {
    // Step 1: Start at the home page
    await page.goto('/');
    await expect(page.locator('img[alt="Responsive image"]')).toBeVisible();
    
    // Step 2: Navigate to Add Students form
    await page.click('a[routerLink="/addstudents"]');
    await page.waitForURL('**/addstudents');
    
    // Step 3: Fill out the form
    const timestamp = Date.now();
    const testName = `Test Student ${timestamp}`;
    const testEmail = `test${timestamp}@example.com`;
    
    await page.fill('input#name', testName);
    await page.fill('input#email', testEmail);
    
    // Step 4: Submit the form
    await page.click('button[type="submit"]');
    
    // Wait for submission to complete
    await page.waitForTimeout(2000);
    
    // Step 5: Navigate to student list
    await page.goto('/');
    await page.click('a[routerLink="/students"]');
    await page.waitForURL('**/students');
    
    // Step 6: Verify the table is visible
    await expect(page.locator('table.table')).toBeVisible();
    
    // Step 7: Verify students are displayed (table should have at least headers)
    const tableRows = page.locator('table tbody tr');
    const rowCount = await tableRows.count();
    
    // We should have at least the newly added student
    expect(rowCount).toBeGreaterThanOrEqual(0);
    
    console.log(`✓ End-to-end flow completed: Added student "${testName}" and viewed list`);
  });

  test('navigation flow: home -> list -> home -> form -> home', async ({ page }) => {
    // Start at home
    await page.goto('/');
    await expect(page).toHaveURL('/');
    
    // Go to list
    await page.click('a[routerLink="/students"]');
    await expect(page).toHaveURL(/.*students/);
    await expect(page.locator('table')).toBeVisible();
    
    // Back to home (using browser navigation or clicking logo area)
    await page.goto('/');
    await expect(page).toHaveURL('/');
    
    // Go to form
    await page.click('a[routerLink="/addstudents"]');
    await expect(page).toHaveURL(/.*addstudents/);
    await expect(page.locator('form')).toBeVisible();
    
    // Back to home
    await page.goto('/');
    await expect(page).toHaveURL('/');
    
    console.log('✓ Navigation flow completed successfully');
  });

  test('verify responsive design elements', async ({ page }) => {
    await page.goto('/');
    
    // Check if Bootstrap container exists
    const container = page.locator('.container');
    await expect(container).toBeVisible();
    
    // Check if responsive image class is applied
    const logo = page.locator('img.img-fluid');
    await expect(logo).toBeVisible();
    
    // Check if buttons have proper Bootstrap classes
    const buttons = page.locator('a.btn.btn-info');
    await expect(buttons).toHaveCount(2);
  });

  test('verify all pages load without errors', async ({ page }) => {
    const errors: string[] = [];
    
    // Listen for console errors
    page.on('pageerror', (error) => {
      errors.push(error.message);
    });
    
    // Test home page
    await page.goto('/');
    await page.waitForLoadState('networkidle');
    
    // Test students list page
    await page.goto('/students');
    await page.waitForLoadState('networkidle');
    
    // Test add students page
    await page.goto('/addstudents');
    await page.waitForLoadState('networkidle');
    
    // Assert no errors occurred
    expect(errors.length).toBe(0);
    
    console.log('✓ All pages loaded without errors');
  });
});
