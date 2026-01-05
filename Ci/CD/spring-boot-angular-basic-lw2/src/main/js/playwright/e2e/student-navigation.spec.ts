import { test, expect } from "@playwright/test";

test.describe("Navigation Tests", () => {
	test("should display main page with navigation buttons", async ({
		page,
	}) => {
		await page.goto("/");

		// Check if the TBZ logo is visible
		const logo = page.locator('img[alt="Responsive image"]');
		await expect(logo).toBeVisible();

		// Check if navigation buttons exist
		const listStudentsBtn = page.locator("a.btn.btn-info", {
			hasText: "List Students",
		});
		const addStudentsBtn = page.locator("a.btn.btn-info", {
			hasText: "Add Students",
		});

		await expect(listStudentsBtn).toBeVisible();
		await expect(addStudentsBtn).toBeVisible();
	});

	test("should navigate to students list", async ({ page }) => {
		await page.goto("/");

		// Click on "List Students" button
		await page.click('a[routerLink="/students"]');

		// Wait for navigation
		await page.waitForURL("**/students");

		// Verify we're on the students list page
		await expect(page.locator("table.table")).toBeVisible();
	});

	test("should navigate to add students form", async ({ page }) => {
		await page.goto("/");

		// Click on "Add Students" button
		await page.click('a[routerLink="/addstudents"]');

		// Wait for navigation
		await page.waitForURL("**/addstudents");

		// Verify we're on the add students page
		await expect(page.locator("form")).toBeVisible();
		await expect(page.locator("input#name")).toBeVisible();
		await expect(page.locator("input#email")).toBeVisible();
	});
});
