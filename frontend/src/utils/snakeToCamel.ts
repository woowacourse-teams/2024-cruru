/* eslint-disable @typescript-eslint/no-explicit-any */
/* eslint-disable no-else-return */
const toCamelCase = (str: string): string => str.replace(/_([a-z])/g, (_, letter) => letter.toUpperCase());

/**
 * 사용 예시 const camelCaseObject = keysToCamelCase(snakeCaseObject);
 * @param obj
 * @returns
 */
export default function snakeToCamel(obj: any): any {
  if (Array.isArray(obj)) {
    return obj.map((item) => snakeToCamel(item));
  } else if (obj !== null && obj.constructor === Object) {
    const newObj: any = {};
    Object.keys(obj).forEach((key) => {
      newObj[toCamelCase(key)] = snakeToCamel(obj[key]);
    });
    return newObj;
  }
  return obj;
}
