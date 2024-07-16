import { Process } from '@/types/process';
import S from './style';
import { EllipsisIcon } from '@/assets/icons';
import formatDate from '@/utils/formatDate';

interface ProcessColumnProps extends React.PropsWithChildren {
  process: Process;
}

export default function ProcessColumn({ process }: ProcessColumnProps) {
  return (
    <S.ProcessWrapper>
      <S.Header>
        <S.Title>Process Name</S.Title>
        <S.OptionButton>
          <img
            alt="옵션 버튼"
            src={EllipsisIcon}
          />
        </S.OptionButton>
      </S.Header>
      <S.ApplicantList>
        {process.applicants.map(({ id, name, createdAt }) => (
          <li key={id}>
            <h3>{name}</h3>
            <small>{`지원 일자: ${formatDate(createdAt)}`}</small>
          </li>
        ))}
      </S.ApplicantList>
    </S.ProcessWrapper>
  );
}
