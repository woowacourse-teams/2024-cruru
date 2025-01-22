import { HiOutlineDocumentText } from 'react-icons/hi2';

import { applyMutations } from '@hooks/apply';
import { useParams } from 'react-router-dom';
import S from './style';

export default function CSVDownloadButton() {
  const { applyFormId = '' } = useParams();
  const { mutate: downloadCsv, isPending } = applyMutations.useDownloadCsv();

  const requestAndDownloadCsv = () => {
    if (applyFormId) {
      downloadCsv({ applyFormId });
    }
  };

  return (
    <S.CSVButton
      onClick={requestAndDownloadCsv}
      disabled={isPending}
    >
      <HiOutlineDocumentText size={14} />
      CSV 추출
    </S.CSVButton>
  );
}
