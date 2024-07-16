import S from './style';
import Dropdown from '../Dropdown';

export default function ApplicantCard() {
  return (
    <S.CardContainer>
      <S.CardDetail>
        <S.CardHeader>김다은</S.CardHeader>
        <S.CardDate>지원 일자: 24. 07. 15</S.CardDate>
      </S.CardDetail>
      <S.DropdownWrapper>
        <Dropdown
          defaultSelected="단계"
          items={['프로세스1', '프로세스2', '프로세스3']}
        />
      </S.DropdownWrapper>
    </S.CardContainer>
  );
}
