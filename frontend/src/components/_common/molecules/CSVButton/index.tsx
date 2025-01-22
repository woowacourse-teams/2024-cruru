import { HiOutlineDocumentText } from 'react-icons/hi2';

import S from './style';

export default function CSVDownloadButton() {
  return (
    <S.CSVButton>
      <HiOutlineDocumentText size={14} />
      CSV 추출
    </S.CSVButton>
  );
}
