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
        <Dropdown />
      </S.DropdownWrapper>
    </S.CardContainer>
  );
}
