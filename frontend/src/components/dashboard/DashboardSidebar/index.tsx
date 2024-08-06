import Accordion from '@components/common/Accordion';
import S from './style';

export default function DashboardSidebar() {
  // TODO: URL Param과 같은 방법으로 현재 공고가 Selected 인지 확인할 수 있도록 합니다.

  const options = [
    { text: '프론트엔드 7기 모집', isSelected: true },
    { text: '백엔드 7기 모집', isSelected: false },
    { text: '안드로이드 7기 모집', isSelected: false },
  ];

  return (
    <S.Container>
      <S.Logo>ㅋㄹㄹ</S.Logo>
      <S.Contents>
        <Accordion title="공고">
          {options.map(({ text, isSelected }, index) => (
            // eslint-disable-next-line react/no-array-index-key
            <Accordion.ListItem key={index}>
              <S.NavButton isSelected={isSelected}>{text}</S.NavButton>
            </Accordion.ListItem>
          ))}
        </Accordion>
      </S.Contents>
    </S.Container>
  );
}
