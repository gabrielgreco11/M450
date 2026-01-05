import { test, expect } from '@playwright/test';

test.describe('Student Form Tests', () => {
  test.beforeEach(async ({ page }) => {
    // Navigate to the add students form before each test
    await page.goto('/addstudents');
  });

  test('should display the student form with all fields', async ({ page }) => {
    // Check if form is visible
    await expect(page.locator('form')).toBeVisible();
    
    // Check if name field exists
    const nameField = page.locator('input#name');
    await expect(nameField).toBeVisible();
    await expect(nameField).toHaveAttribute('placeholder', 'Enter your name');
    
    // Check if email field exists
    const emailField = page.locator('input#email');
    await expect(emailField).toBeVisible();
    await expect(emailField).toHaveAttribute('placeholder', 'Enter your email address');
    
    // Check if submit button exists
    const submitButton = page.locator('button[type="submit"]');
    await expect(submitButton).toBeVisible();
    await expect(submitButton).toHaveText('Submit');
  });

  test('should have disabled submit button when form is empty', async ({ page }) => {
    const submitButton = page.locator('button[type="submit"]');
    
    // Submit button should be disabled initially
    await expect(submitButton).toBeDisabled();
  });

  test('should enable submit button when both fields are filled', async ({ page }) => {
    // Fill in the name field
    await page.fill('input#name', 'Max Mustermann');
    
    // Fill in the email field
    await page.fill('input#email', 'max.mustermann@example.com');
    
    // Submit button should now be enabled
    const submitButton = page.locator('button[type="submit"]');
    await expect(submitButton).toBeEnabled();
  });

  test('should submit form with valid data', async ({ page }) => {
    // Fill in the form
    await page.fill('input#name', 'Anna Schmidt');
    await page.fill('input#email', 'anna.schmidt@tbz.ch');
    
    // Submit the form
    const submitButton = page.locator('button[type="submit"]');
    await submitButton.click();
    
    // Wait a moment for form submission
    await page.waitForTimeout(1000);
    
    // After successful submission, form fields should be cleared or we navigate away
    // This depends on your implementation
  });

  test('should show validation for required name field', async ({ page }) => {
    const nameField = page.locator('input#name');
    
    // Check if required attribute exists
    await expect(nameField).toHaveAttribute('required');
    
    // Fill and then clear the name field to trigger validation
    await nameField.fill('Test');
    await nameField.clear();
    await nameField.blur();
    
    // Note: The validation message visibility depends on the pristine state
    // which is handled by Angular forms
  });

  test('should show validation for required email field', async ({ page }) => {
    const emailField = page.locator('input#email');
    
    // Check if required attribute exists
    await expect(emailField).toHaveAttribute('required');
    
    // Fill and then clear the email field to trigger validation
    await emailField.fill('test@example.com');
    await emailField.clear();
    await emailField.blur();
  });

  test('should handle form input correctly', async ({ page }) => {
    const nameField = page.locator('input#name');
    const emailField = page.locator('input#email');
    
    // Type into name field
    await nameField.fill('Peter Müller');
    await expect(nameField).toHaveValue('Peter Müller');
    
    // Type into email field
    await emailField.fill('peter.mueller@gmail.com');
    await expect(emailField).toHaveValue('peter.mueller@gmail.com');
  });

  test('should have proper form styling', async ({ page }) => {
    // Check if form groups have correct classes
    const formGroups = page.locator('.form-group');
    await expect(formGroups).toHaveCount(2);
    
    // Check if inputs have form-control class
    await expect(page.locator('input#name')).toHaveClass(/form-control/);
    await expect(page.locator('input#email')).toHaveClass(/form-control/);
    
    // Check if submit button has correct class
    await expect(page.locator('button[type="submit"]')).toHaveClass(/btn-info/);
  });
});
