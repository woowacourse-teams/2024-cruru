import { http, HttpResponse } from 'msw';

import { PROCESSES } from '@api/endPoint';
import processes from '../processMockData.json';

const processHandlers = [http.get(PROCESSES, () => HttpResponse.json(processes))];

export default processHandlers;
