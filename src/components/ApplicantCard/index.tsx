import S from './style';
import Dropdown from '../Dropdown';
import formatDate from '@/utils/formatDate';

interface ApplicantCardProps {
  name: string;
  createdAt: string;
  processNameList: string[];
}

export default function ApplicantCard({ name, createdAt, processNameList }: ApplicantCardProps) {
  return (
    <S.CardContainer>
      <S.CardDetail>
        <S.CardHeader>{name}</S.CardHeader>
        <S.CardDate>{`지원 일자: ${formatDate(createdAt)}`}</S.CardDate>
      </S.CardDetail>
      <S.DropdownWrapper>
        <Dropdown
          defaultSelectedValue={'단계'}
          processNameList={processNameList}
        />
      </S.DropdownWrapper>
    </S.CardContainer>
  );
}
