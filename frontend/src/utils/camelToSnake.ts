const toSnakeCase = (str: string): string => str.replace(/[A-Z]/g, (letter) => `_${letter.toLowerCase()}`);

const camelToSnake = (obj: Record<string, unknown>) =>
  Object.keys(obj).reduce(
    (acc, key) => {
      const snakeKey = toSnakeCase(key);
      acc[snakeKey] = obj[key];
      return acc;
    },
    {} as Record<string, unknown>,
  );

export default camelToSnake;
