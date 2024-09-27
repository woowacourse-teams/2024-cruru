import React from 'react';
import server from '@mocks/server';

global.React = React;

beforeAll(() => {
  server.listen({ onUnhandledRequest: 'error' });

  jest.spyOn(Storage.prototype, 'setItem');
  jest.spyOn(Storage.prototype, 'getItem');
  jest.spyOn(Storage.prototype, 'removeItem');
});

afterEach(() => {
  server.resetHandlers();

  jest.restoreAllMocks();
});

afterAll(() => {
  server.close();
});
