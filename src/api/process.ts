export const getProcesses = async ({ id }: { id: number }) => {
  const response = await fetch(`/api/processes?dashboard_id=${id}`, {
    headers: {
      Accept: 'application/json',
    },
  });

  if (!response.ok) {
    throw new Error('Network response was not ok');
  }

  const data = await response.json();
  return data;
};
