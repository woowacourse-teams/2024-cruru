import { Process } from '@/types/process';
import { EllipsisIcon } from '@/assets/icons';

import S from './style';
import ApplicantCard from '../ApplicantCard';
import Button from '../Button';

import useProcess from '@/hooks/useProcess';

interface IProcessColumnProps extends React.PropsWithChildren {
  process: Process;
}

export default function ProcessColumn({ process }: IProcessColumnProps) {
  const { processNameList } = useProcess();

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
        {process.applicants.map(({ applicant_id, applicant_name, created_at }) => (
          <ApplicantCard
            key={applicant_id}
            name={applicant_name}
            createdAt={created_at}
            processNameList={processNameList}
          />
        ))}
      </S.ApplicantList>
    </S.ProcessWrapper>
  );
}
