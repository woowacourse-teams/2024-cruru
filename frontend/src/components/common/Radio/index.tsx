import S from './style';

interface RadioProps {
  checked: boolean;
  diameter?: number;
  onChange: () => void;
}

export default function Radio({ checked, diameter, onChange }: RadioProps) {
  return (
    <S.RadioContainer onClick={onChange}>
      <S.RadioOuter
        diameter={diameter}
        checked={checked}
      >
        <S.RadioInner
          diameter={diameter}
          checked={checked}
        />
      </S.RadioOuter>
    </S.RadioContainer>
  );
}
