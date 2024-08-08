import S from './style';

interface RecruitmentCardStatProps {
  label: string;
  number: number;
  isTotalStats?: boolean;
}

export default function RecruitmentCardStat({ label, number, isTotalStats = false }: RecruitmentCardStatProps) {
  return (
    <S.StatContainer isTotalStats={isTotalStats}>
      {label}
      <S.StatNumber>{number}</S.StatNumber>
    </S.StatContainer>
  );
}
