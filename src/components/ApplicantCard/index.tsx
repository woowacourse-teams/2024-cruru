import S from './style';
import Dropdown from '../Dropdown';

export default function ApplicantCard() {
  const items = [
    { name: '프로세스1', onClick: () => console.log('프로세스1') },
    { name: '프로세스2', onClick: () => console.log('프로세스2') },
    { name: '프로세스3', onClick: () => console.log('프로세스3') },
  ];
  return (
    <S.CardContainer>
      <S.CardDetail>
        <S.CardHeader>김다은</S.CardHeader>
        <S.CardDate>지원 일자: 24. 07. 15</S.CardDate>
      </S.CardDetail>
      <S.DropdownWrapper>
        <Dropdown
          defaultSelected="단계"
          items={items}
        />
      </S.DropdownWrapper>
    </S.CardContainer>
  );
}
