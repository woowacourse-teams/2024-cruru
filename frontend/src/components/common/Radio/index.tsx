import S from './style';

interface RadioProps {
  isChecked: boolean;
  diameter?: string;
  onChange: () => void;
}

export default function Radio({ isChecked, diameter, onChange }: RadioProps) {
  return (
    <S.RadioContainer onClick={onChange}>
      <S.RadioOuter
        diameter={diameter}
        checked={isChecked}
      >
        <S.RadioInner checked={isChecked} />
      </S.RadioOuter>
    </S.RadioContainer>
  );
}
