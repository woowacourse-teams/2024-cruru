
const toCamelCase = (str: string): string => {
    return str.replace(/_([a-z])/g, (_, letter) => letter.toUpperCase());
  };
  
  /**
   * 사용 예시 const camelCaseObject = keysToCamelCase(snakeCaseObject);
   * @param obj 
   * @returns 
   */
 export default function keysToCamelCase (obj: any): any {
    if (Array.isArray(obj)) {
      return obj.map((item) => keysToCamelCase(item));
    } else if (obj !== null && obj.constructor === Object) {
      const newObj: any = {};
      Object.keys(obj).forEach((key) => {
        newObj[toCamelCase(key)] = keysToCamelCase(obj[key]);
      });
      return newObj;
    }
    return obj;
  };
  


