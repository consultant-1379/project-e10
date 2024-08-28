import { describe, expect, test } from 'vitest'
import { setupServer } from 'msw/node'
import { rest } from 'msw'
import Backend from '../util/Backend'

const server = setupServer(
  rest.post(Backend.baseUrl + "list", (req, res, ctx) => {
    return res(ctx.json({
      node: "value",
    }))
  }),
  rest.get(Backend.baseUrl + "by-session", (req, res, ctx) => {
    return res(ctx.json({
      node: "value",
    }))
  }),
  rest.get(Backend.baseUrl + "GenerateToken", (req, res, ctx) => {
    return res(ctx.json({
      node: "value",
    }))
  }),
  rest.get(Backend.baseUrl + "graph", (req, res, ctx) => {
    return res(ctx.json({
      node: "value",
    }))
  }),
)

beforeAll(() => server.listen())
afterEach(() => server.resetHandlers())
afterAll(() => server.close())

describe('Backend', () => {
  test('Should make StoreResults call', () => {
    expect(Backend.baseUrl).toBe("http://localhost:8080/form_results/")
    expect(Backend.storeResults([{"val": "val"}, {"val": "val"}], {})).resolves.toEqual(undefined)
  })

  test('Should make RetrieveRecord call', () => {
    expect(Backend.baseUrl).toBe("http://localhost:8080/form_results/")
    expect(Backend.retrieveRecord(1234, 1234)).resolves.toEqual({"node": "value"})
  })

  test('Should make GetUser call', () => {
    expect(Backend.baseUrl).toBe("http://localhost:8080/form_results/")
    expect(Backend.getUser()).resolves.toEqual({"node": "value"})
  })
  test('Should make Graph call', () => {
    expect(Backend.baseUrl).toBe("http://localhost:8080/form_results/")
    expect(Backend.getGraph()).resolves.toEqual('{"node":"value"}')
  })
})
