import Dropdown from '@components/common/Dropdown';
import Button from '@components/common/Button';
import S from './style';

const items = [
  { id: 1, name: 'Stage 1', onClick: () => console.log('Stage 1') },
  { id: 2, name: 'Stage 2', onClick: () => console.log('Stage 2') },
  { id: 3, name: 'Stage 3', onClick: () => console.log('Stage 3') },
];

export default function ApplicantBaseDetail() {
  return (
    <S.Container>
      <S.Title>지원자 이름</S.Title>
      <S.ActionRow>
        <Dropdown
          initValue="모집 단계 이동"
          size="sm"
          items={items}
          width={112}
          isShadow={false}
        />
        <Button
          size="sm"
          color="error"
        >
          불합격
        </Button>
      </S.ActionRow>
      <S.DetailContainer>
        <S.DetailRow>
          <S.Label>이메일</S.Label>
          <S.Value>midekuna@gmail.com</S.Value>
        </S.DetailRow>
        <S.DetailRow>
          <S.Label>연락처</S.Label>
          <S.Value>01999999999</S.Value>
        </S.DetailRow>
        <S.DetailRow>
          <S.Label>접수일</S.Label>
          <S.Value>2024.07.05</S.Value>
        </S.DetailRow>
      </S.DetailContainer>
    </S.Container>
  );
}
