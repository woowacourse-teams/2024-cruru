import { Process } from '../types/process';

export const processMockData: Process[] = [
  {
    processId: 1,
    orderIndex: 0,
    name: '서류 검토',
    description: '설명',
    applicants: [
      {
        applicant_id: 1,
        applicant_name: '아르',
        created_at: '2024-07-15T14:30:00.000Z',
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
        applicant_id: 2,
        applicant_name: '렛서',
        created_at: '2024-07-15T14:50:00.000Z',
      },
      {
        applicant_id: 3,
        applicant_name: '러기',
        created_at: '2024-07-15T15:10:00.000Z',
      },
    ],
  },
];

export default processMockData;
