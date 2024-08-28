import { describe, expect, test } from 'vitest'
import { render, screen } from '@testing-library/react'
import History from '../routes/history/history'

describe('History', () => {
  test("Should render Session ID Input", () => {
    render(<History />);
    const title = screen.getByText('History');
    expect(title).toBeDefined();
    const sessionIdInput = screen.getByPlaceholderText('Session ID');
    expect(sessionIdInput).toBeDefined();
  });
})
