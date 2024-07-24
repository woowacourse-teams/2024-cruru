import S from './style';

interface RadioProps {
  checked: boolean;
  diameter?: string;
  onChange: () => void;
}

export default function Radio({ checked, diameter, onChange }: RadioProps) {
  return (
    <S.RadioContainer onClick={onChange}>
      <S.RadioOuter
        diameter={diameter}
        checked={checked}
      >
        <S.RadioInner checked={checked} />
      </S.RadioOuter>
    </S.RadioContainer>
  );
}
