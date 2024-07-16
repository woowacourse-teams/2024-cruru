import { Process } from '@/types/process';
import { EllipsisIcon } from '@/assets/icons';
import ApplicantCard from '../ApplicantCard';

import S from './style';

interface ProcessColumnProps extends React.PropsWithChildren {
  process: Process;
  processNameList: string[];
}

export default function ProcessColumn({ process, processNameList }: ProcessColumnProps) {
  return (
    <S.ProcessWrapper>
      <S.Header>
        <S.Title>{process.name}</S.Title>
        <S.OptionButton>
          <img
            alt="옵션 버튼"
            src={EllipsisIcon}
          />
        </S.OptionButton>
      </S.Header>
      <S.ApplicantList>
        {process.applicants.map(({ id, name, createdAt }) => (
          <ApplicantCard
            key={id}
            name={name}
            createdAt={createdAt}
            processNameList={processNameList}
          />
        ))}
      </S.ApplicantList>
    </S.ProcessWrapper>
  );
}
