import { describe, expect, test } from 'vitest'
import { render, screen, fireEvent } from '@testing-library/react'
import { MemoryRouter } from 'react-router-dom'
import Survey from '../routes/survey/survey'
import { setupServer } from 'msw/node'
import { rest } from 'msw'
import Backend from '../util/Backend'

const server = setupServer(
  rest.get(Backend.baseUrl + "GenerateToken", (req, res, ctx) => {
    return res(ctx.json({
      sessionId: 1234,
      userId: 3343,
      sessinPin: 2342,
    }))
  })
)

beforeAll(() => server.listen())
afterEach(() => server.resetHandlers())
afterAll(() => server.close())

describe('Survey', () => {
  test('should render', () => {
    expect(Survey).toBeDefined()
  })

  test('Should render expected title', () => {
    render(
      <MemoryRouter>
        <Survey/>
      </MemoryRouter>
    )
    const title = screen.getByText('Cloud Native Maturity Matrix')
    expect(title).toBeDefined()
  })

  test('Should render Yes and No buttons', () => {
    render(
      <MemoryRouter>
        <Survey/>
      </MemoryRouter>
    )
    const yesButton = screen.getByText('Yes');
    const noButton = screen.getByText('No');
    expect(yesButton).toBeDefined();
    expect(noButton).toBeDefined();
  });

  test('Should hide Yes and No buttons and show follow-up questions on clicking No', () => {
    render(
      <MemoryRouter>
        <Survey/>
      </MemoryRouter>
    )
    const noButton = screen.getByText('No');
    fireEvent.click(noButton);

    // Check if Yes and No buttons are not in the document anymore
    expect(screen.queryByText('Yes')).toBeNull();
    expect(screen.queryByText('No')).toBeNull();
  });
})
