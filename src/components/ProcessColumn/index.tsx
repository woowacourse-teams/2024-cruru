import { Process } from '@/types/process';
import { EllipsisIcon } from '@/assets/icons';
import ApplicantCard from '../ApplicantCard';

import S from './style';
import Button from '../Button';

interface ProcessColumnProps extends React.PropsWithChildren {
  process: Process;
  processNameList: string[];
}

export default function ProcessColumn({ process, processNameList }: ProcessColumnProps) {
  return (
    <S.ProcessWrapper>
      <S.Header>
        <S.Title>{process.name}</S.Title>
        <Button
          type="button"
          onClick={() => console.log('프로세스 옵션 버튼이 클릭되었습니다')}
        >
          <S.OptionButtonContainer>
            <img
              alt="옵션 버튼 아이콘"
              src={EllipsisIcon}
            />
          </S.OptionButtonContainer>
        </Button>
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
