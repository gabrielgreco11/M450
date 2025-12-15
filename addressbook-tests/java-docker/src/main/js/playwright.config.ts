import { defineConfig } from '@playwright/test';


export default defineConfig({
testDir: './tests',
timeout: 30_000,
use: {
baseURL: 'http://localhost:4200',
browserName: 'firefox',
headless: true,
screenshot: 'only-on-failure',
video: 'retain-on-failure'
},
});