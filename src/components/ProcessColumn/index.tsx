import { Process } from '@/types/process';

import S from './style';
import ApplicantCard from '../ApplicantCard';
import useProcess from '@/hooks/useProcess';

interface ProcessColumnProps {
  process: Process;
}

export default function ProcessColumn({ process }: ProcessColumnProps) {
  const { processNameList } = useProcess();

  const menuItemsList = processNameList.map((name, index) => ({
    id: String(index),
    name,
    onClick: () => console.log('click'),
  }));

  return (
    <S.ProcessWrapper>
      <S.Header>
        <S.Title>{process.name}</S.Title>
      </S.Header>
      <S.ApplicantList>
        {process.applicants.map(({ applicantId, applicantName, createdAt }) => (
          <ApplicantCard
            key={applicantId}
            name={applicantName}
            createdAt={createdAt}
            popOverMenuItems={menuItemsList}
          />
        ))}
      </S.ApplicantList>
    </S.ProcessWrapper>
  );
}
