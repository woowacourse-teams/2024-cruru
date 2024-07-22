import { http, HttpResponse } from 'msw';

import { GET_PROCESSES } from '@api/endPoint';
import processes from '../processMockData.json';

const processHandlers = [http.get(GET_PROCESSES, () => HttpResponse.json(processes))];

export default processHandlers;
