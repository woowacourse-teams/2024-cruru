import snakeToCamel from '@utils/snakeToCamel';
import { createParams } from './utils';
import { PROCESSES } from './endPoint';

export const getProcesses = async ({ id }: { id: number }) => {
  const response = await fetch(`${PROCESSES}/api/v1?${createParams({ dashboard_id: String(id) })}`, {
    headers: {
      Accept: 'application/json',
    },
  });

  if (!response.ok) {
    throw new Error('Network response was not ok');
  }

  const data = await response.json();
  return snakeToCamel(data);
};
