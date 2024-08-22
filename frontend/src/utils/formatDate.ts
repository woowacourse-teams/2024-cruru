import { ISO8601 } from '@customTypes/common';

export default function formatDate(dateString: ISO8601 | string) {
  const date = new Date(dateString);

  const year = date.getFullYear().toString().slice(-2);
  const month = (date.getMonth() + 1).toString().padStart(2, '0');
  const day = date.getDate().toString().padStart(2, '0');

  return `${year}. ${month}. ${day}`;
}

export function getCleanDateString(dateString?: ISO8601 | string) {
  const date = dateString ? new Date(dateString) : new Date();
  date.setHours(date.getHours());
  return new Date(date.toDateString());
}

/**
 * ISO 8601 형식의 날짜 문자열을 로케일에 맞게 변환합니다.
 *
 * @param {Object} params - 함수 매개변수 객체입니다.
 * @param {ISO8601} [params.date] - 변환할 ISO 8601 형식의 날짜 문자열입니다. 제공되지 않으면 빈 문자열을 반환합니다.
 * @param {boolean} [params.time=false] - 시간을 포함할지 여부를 나타내는 불리언 값입니다. 기본값은 `false`입니다.
 * @param {Intl.DateTimeFormatOptions} [params.options] - 날짜 및 시간 형식을 지정하는 옵션 객체입니다.
 *
 * @returns {string} 변환된 로케일 날짜 문자열을 반환합니다. `date`가 제공되지 않으면 빈 문자열을 반환합니다.
 */
export const ISOtoLocaleString = ({
  date,
  time = false,
  options,
}: {
  date?: ISO8601;
  time?: boolean;
  options?: Intl.DateTimeFormatOptions;
}) => {
  if (!date) return '';

  if (time) return new Date(date).toLocaleString('ko-Kr', { ...options, timeZone: 'Asia/Seoul' });
  return new Date(date).toLocaleDateString('ko-Kr', { ...options, timeZone: 'Asia/Seoul' });
};
