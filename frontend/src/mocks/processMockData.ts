import { Process } from '../types/process';

export const processMockData: Process[] = [
  {
    processId: 1,
    orderIndex: 0,
    name: '서류 검토',
    description: '설명',
    applicants: [
      {
        applicantId: 1,
        applicantName: '아르',
        createdAt: '2024-07-15T14:30:00.000Z',
      },
    ],
  },
  {
    processId: 2,
    name: '면접',
    orderIndex: 1,
    description: '설명',
    applicants: [
      {
        applicantId: 2,
        applicantName: '렛서',
        createdAt: '2024-07-15T14:50:00.000Z',
      },
      {
        applicantId: 3,
        applicantName: '러기',
        createdAt: '2024-07-15T15:10:00.000Z',
      },
    ],
  },
];

export default processMockData;
