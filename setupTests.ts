import React from 'react';
import server from '@/mocks/server';
import '@testing-library/jest-dom';

global.React = React;

beforeAll(() => {
  server.listen({ onUnhandledRequest: 'error' });
});

afterEach(() => {
  server.resetHandlers();
});

afterAll(() => {
  server.close();
});
