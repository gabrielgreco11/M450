import { test, expect } from '@playwright/test';

test.describe('Student List Tests', () => {
  test('should display student list table with headers', async ({ page }) => {
    await page.goto('/students');
    
    // Check if table is visible
    const table = page.locator('table.table-bordered');
    await expect(table).toBeVisible();
    
    // Check table headers
    await expect(page.locator('th', { hasText: '#' })).toBeVisible();
    await expect(page.locator('th', { hasText: 'Name' })).toBeVisible();
    await expect(page.locator('th', { hasText: 'Email' })).toBeVisible();
  });

  test('should display students in the table', async ({ page }) => {
    await page.goto('/students');
    
    // Wait for the table to load
    await page.waitForSelector('table tbody');
    
    // Check if table body exists
    const tableBody = page.locator('table tbody');
    await expect(tableBody).toBeVisible();
    
    // Count rows - we expect at least the table structure
    const rows = page.locator('table tbody tr');
    const rowCount = await rows.count();
    
    // If there are students, verify the structure
    if (rowCount > 0) {
      const firstRow = rows.first();
      await expect(firstRow.locator('td').nth(0)).toBeVisible(); // ID
      await expect(firstRow.locator('td').nth(1)).toBeVisible(); // Name
      await expect(firstRow.locator('td').nth(2)).toBeVisible(); // Email
    }
  });

  test('should have clickable email links', async ({ page }) => {
    await page.goto('/students');
    
    // Wait for table to load
    await page.waitForSelector('table tbody');
    
    // Check if there are any email links
    const emailLinks = page.locator('a[href^="mailto:"]');
    const emailCount = await emailLinks.count();
    
    if (emailCount > 0) {
      const firstEmailLink = emailLinks.first();
      await expect(firstEmailLink).toBeVisible();
      
      // Verify the href attribute contains 'mailto:'
      const href = await firstEmailLink.getAttribute('href');
      expect(href).toContain('mailto:');
    }
  });

  test('should apply correct CSS classes to table', async ({ page }) => {
    await page.goto('/students');
    
    // Verify table has correct Bootstrap classes
    const table = page.locator('table');
    await expect(table).toHaveClass(/table/);
    await expect(table).toHaveClass(/table-bordered/);
    await expect(table).toHaveClass(/table-striped/);
    
    // Verify thead has correct class
    const thead = page.locator('thead');
    await expect(thead).toHaveClass(/thead-dark/);
  });
});
